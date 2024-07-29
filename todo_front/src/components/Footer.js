import React from 'react';
import '../assets/styles/FooterStyle.css';
import { useNavigate } from 'react-router-dom';

function Footer() {
  const navigate = useNavigate();

  const navigateTo = path => {
    navigate(path);
  };

  return (
    <div className="footer-container">
      <div className="footer-img">
        <img src="/images/list.png" alt="저장목록" onClick={() => navigateTo('/savelist')}></img>
        <img
          src="/images/add.png"
          alt="홈"
          onClick={() => navigateTo('/home')}
        ></img>
        <img
          src="/images/mypage.png"
          alt="마이페이지"
          onClick={() => navigateTo('/mypage')}
        ></img>
      </div>
    </div>
  );
}

export default Footer;
