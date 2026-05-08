import { useState } from 'react'
import './App.css'
import KnowledgePage from './pages/KnowledgePage'
import ChatTestPage from './pages/ChatTestPage'

function App() {
  const [activeTab, setActiveTab] = useState<'knowledge' | 'chat'>('knowledge')

  return (
    <div className="app-shell">
      <header className="app-topbar">
        <div>
          <h1>CareDesk AI MVP</h1>
          <p>Spring Boot API 테스트용 Knowledge / Chat 화면</p>
        </div>
        <div className="tab-buttons">
          <button
            type="button"
            className={activeTab === 'knowledge' ? 'tab active' : 'tab'}
            onClick={() => setActiveTab('knowledge')}
          >
            Knowledge 관리
          </button>
          <button
            type="button"
            className={activeTab === 'chat' ? 'tab active' : 'tab'}
            onClick={() => setActiveTab('chat')}
          >
            AI 상담 테스트
          </button>
        </div>
      </header>

      <main className="app-content">
        {activeTab === 'knowledge' ? <KnowledgePage /> : <ChatTestPage />}
      </main>
    </div>
  )
}

export default App
