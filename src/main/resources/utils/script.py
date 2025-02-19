import pandas as pd
import json

# Load the CSV files into DataFrames with skipinitialspace
invoices_df = pd.read_csv('invoices.csv', skipinitialspace=True)
transactions_df = pd.read_csv('transactions.csv', skipinitialspace=True)

# Print the column names to check for issues
print("Invoices Columns:", invoices_df.columns.tolist())
print("Transactions Columns:", transactions_df.columns.tolist())

# Merge the DataFrames on 'invoice_id'
merged_df = pd.merge(invoices_df, transactions_df, on='invoice_id', how='left')

# Check the merged DataFrame
print("Merged DataFrame:")
print(merged_df.head())

# Construct the JSON object
invoices_json = []

try:
    for invoice_id, group in merged_df.groupby('invoice_id'):
        invoice_data = {
            'id': int(invoice_id),  # Convert to int
            'invoiceNumber': str(group['invoice_number_x'].iloc[0]),  # Use invoice_number_x
            'grossAmount': float(group['gross_amount'].iloc[0]),  # Convert to float
            'gstAmount': float(group['gst_amount_x'].iloc[0]),  # Use gst_amount_x
            'netAmount': float(group['net_amount'].iloc[0]),  # Convert to float
            'receiptDate': group['receipt_date'].iloc[0],  # Keep as string for now
            'paymentDueDate': group['payment_due_date'].iloc[0],  # Keep as string for now
            'totalNumTrxn': int(group['total_no_trxn'].iloc[0]),  # Convert to int
            'transactionList': []  # Initialize as empty list
        }
        
        # Add transactions to the invoice
        for _, transaction in group.iterrows():
            transaction_data = {
                'id': int(transaction['trxn_id']),  # Use the correct key
                'dateReceived': transaction['date_received'],  # Use the correct key
                'transactionDate': transaction['transaction_date'],  # Use the correct key
                'invoiceId': int(invoice_id),  # Set the invoiceId from the parent invoice
                'invoiceNumber': str(transaction['invoice_number_y']),  # Use invoice_number_y
                'billingPeriodStart': transaction['billing_period_start'],  # Use the correct key
                'billingPeriodEnd': transaction['billing_period_end'],  # Use the correct key
                'netTransactionAmount': float(transaction['net_transaction_amount']),  # Use the correct key
                'gstAmount': float(transaction['gst_amount_y'])  # Use gst_amount_y
            }
            invoice_data['transactionsList'].append(transaction_data)  # Append to transactionsList
        
        invoices_json.append(invoice_data)

except KeyError as e:
    print(f"KeyError: {e}")
    print("Available columns in the group:")
    print(group.columns.tolist())
except Exception as e:
    print(f"An error occurred: {e}")

# Convert to JSON string
json_body = json.dumps(invoices_json, indent=4)

# Print or use the JSON body for the POST request
print(json_body)

