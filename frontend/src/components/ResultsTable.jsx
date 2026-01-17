import React from 'react';
import Papa from 'papaparse';

const ResultsTable = ({ results }) => {
    if (!results || results.length === 0) return null;

    const downloadCSV = () => {
        const csvData = results.map(r => ({
            FileName: r.fileName,
            ...r.data
        }));

        const csv = Papa.unparse(csvData);
        const blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });
        const url = URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.setAttribute('href', url);
        link.setAttribute('download', 'invoices_data.csv');
        link.style.visibility = 'hidden';
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    };

    return (
        <div className="results-section">
            <div className="header">
                <h2>Extracted Data</h2>
                <button onClick={downloadCSV}>Download CSV</button>
            </div>
            <table>
                <thead>
                    <tr>
                        <th>File Name</th>
                        <th>Supplier</th>
                        <th>Invoice No</th>
                        <th>Total Amount</th>
                        <th>VAT Amount</th>
                        <th>Issue Date</th>
                        <th>Due Date</th>
                        <th>VS</th>
                        <th>VAT ID</th>
                    </tr>
                </thead>
                <tbody>
                    {results.map((r, index) => (
                        <tr key={index}>
                            <td>{r.fileName}</td>
                            <td>{r.data.supplierName}</td>
                            <td>{r.data.invoiceNumber}</td>
                            <td>{r.data.totalAmount}</td>
                            <td>{r.data.vatAmount}</td>
                            <td>{r.data.issueDate}</td>
                            <td>{r.data.dueDate}</td>
                            <td>{r.data.variableSymbol}</td>
                            <td>{r.data.vatId}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default ResultsTable;
