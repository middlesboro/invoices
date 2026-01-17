import React, { useState } from 'react';
import axios from 'axios';

const UploadComponent = ({ onResults }) => {
    const [files, setFiles] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const handleFileChange = (e) => {
        setFiles(Array.from(e.target.files));
    };

    const handleUpload = async () => {
        if (files.length === 0) {
            alert("Please select at least one file.");
            return;
        }

        setLoading(true);
        setError(null);
        const results = [];

        try {
            for (const file of files) {
                const formData = new FormData();
                formData.append('file', file);

                // Assuming backend runs on 8080 and we have a proxy or direct call
                // In Vite dev, we might need CORS or proxy setup.
                // Assuming simple CORS on backend.
                const response = await axios.post('http://localhost:8081/api/invoices/extract', formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                });

                results.push({
                    fileName: file.name,
                    data: response.data,
                    status: 'Success'
                });
            }
            onResults(results);
        } catch (err) {
            console.error(err);
            setError("Error uploading files. Ensure backend is running.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="upload-section">
            <h2>Upload Invoices</h2>
            <input type="file" multiple accept=".pdf" onChange={handleFileChange} />
            <button onClick={handleUpload} disabled={loading || files.length === 0}>
                {loading ? 'Processing...' : 'Upload & Extract'}
            </button>
            {error && <p className="error">{error}</p>}
        </div>
    );
};

export default UploadComponent;
