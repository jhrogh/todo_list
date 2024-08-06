import React from 'react';
import { BrowserRouter as Router, Route, Routes, useLocation  } from 'react-router-dom';
import Header from './components/Header';
import Footer from './components/Footer';
import ChangePw from './pages/ChangePw';
import FindId from './pages/FindId';
import FindPw from './pages/FindPw';
import Home from './pages/Home';
import Join from './pages/Join';
import Login from './pages/Login';
import Mypage from './pages/Mypage';
import SaveList from './pages/SaveList';
import './App.css';

function AppWrapper() {
  const location = useLocation();
  const path = location.pathname;
  const show = /^(\/home|\/mypage|\/savelist)/.test(path);

  return (
      <div className="app">
        <Header />
        {/* <Nav /> */}
        <main className="main-content">
          <Routes>
            <Route path="/change/pw" element={<ChangePw />} />
            <Route path="/find/id" element={<FindId />} />
            <Route path="/find/pw" element={<FindPw />} />
            <Route path="/home" element={<Home />} />
            <Route path="/join" element={<Join />} />
            <Route path="/" element={<Login />} />
            <Route path="/mypage" element={<Mypage />} />
            <Route path="/savelist" element={<SaveList />} />
          </Routes>
        </main>
        {show && <Footer />}
      </div>
  );
}



function App() {
  return (
    <Router>
      <AppWrapper />
    </Router>
  );
}

export default App;
