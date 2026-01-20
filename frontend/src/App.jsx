import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import './App.css'
import InvoicePage from './components/InvoicePage'
import ChatPage from './components/ChatPage'

function App() {
  return (
    <Router>
      <div className="container">
        <nav style={{ marginBottom: '20px', padding: '10px', borderBottom: '1px solid #ccc' }}>
          <Link to="/" style={{ marginRight: '20px' }}>Invoice Extractor</Link>
          <Link to="/chat">Order Chatbot</Link>
        </nav>

        <Routes>
          <Route path="/" element={<InvoicePage />} />
          <Route path="/chat" element={<ChatPage />} />
        </Routes>
      </div>
    </Router>
  )
}

export default App
