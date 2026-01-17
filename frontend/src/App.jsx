import { useState } from 'react'
import './App.css'
import UploadComponent from './components/UploadComponent'
import ResultsTable from './components/ResultsTable'

function App() {
  const [results, setResults] = useState([]);

  const handleResults = (newResults) => {
    setResults(prev => [...prev, ...newResults]);
  };

  return (
    <div className="container">
      <h1>Invoice Extractor</h1>
      <UploadComponent onResults={handleResults} />
      <ResultsTable results={results} />
    </div>
  )
}

export default App
