import React, { useState } from 'react';
import UploadComponent from './UploadComponent';
import ResultsTable from './ResultsTable';

const InvoicePage = () => {
    const [results, setResults] = useState([]);

    const handleResults = (newResults) => {
        setResults(prev => [...prev, ...newResults]);
    };

    return (
        <div>
            <h1>Invoice Extractor</h1>
            <UploadComponent onResults={handleResults} />
            <ResultsTable results={results} />
        </div>
    );
};

export default InvoicePage;
