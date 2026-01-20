import React, { useState } from 'react';
import './ChatPage.css';

const ChatPage = () => {
    const [input, setInput] = useState('');
    const [messages, setMessages] = useState([
        { sender: 'bot', text: 'Hello! I am your order assistant. Ask me about your order status.' }
    ]);
    const [loading, setLoading] = useState(false);

    const handleSend = async () => {
        if (!input.trim()) return;

        const userMessage = { sender: 'user', text: input };
        setMessages(prev => [...prev, userMessage]);
        setInput('');
        setLoading(true);

        try {
            const response = await fetch('/api/chat', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ message: input })
            });

            if (response.ok) {
                const text = await response.text();
                setMessages(prev => [...prev, { sender: 'bot', text: text }]);
            } else {
                setMessages(prev => [...prev, { sender: 'bot', text: 'Sorry, I encountered an error.' }]);
            }
        } catch (error) {
            console.error('Error sending message:', error);
            setMessages(prev => [...prev, { sender: 'bot', text: 'Sorry, I could not reach the server.' }]);
        } finally {
            setLoading(false);
        }
    };

    const handleKeyDown = (e) => {
        if (e.key === 'Enter') {
            handleSend();
        }
    };

    return (
        <div className="chat-container">
            <h2>Order Chatbot</h2>
            <div className="messages-area">
                {messages.map((msg, index) => (
                    <div key={index} className={`message ${msg.sender}`}>
                        <div className="message-bubble">
                            {msg.text}
                        </div>
                    </div>
                ))}
                {loading && <div className="message bot"><div className="message-bubble">Thinking...</div></div>}
            </div>
            <div className="input-area">
                <input
                    type="text"
                    value={input}
                    onChange={(e) => setInput(e.target.value)}
                    onKeyDown={handleKeyDown}
                    placeholder="Type your message..."
                    disabled={loading}
                />
                <button onClick={handleSend} disabled={loading || !input.trim()}>Send</button>
            </div>
        </div>
    );
};

export default ChatPage;
