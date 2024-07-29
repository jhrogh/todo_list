import React from 'react';
import '../assets/styles/FooterStyle.css';

function Footer() {
  return (
    <div className="footer-container">
      <div className="footer-img">
        <img src='/images/list.png' alt="저장목록"></img>
        <img src='/images/add.png' alt="홈"></img>
        <img src='/images/mypage.png' alt="마이페이지"></img>
      </div>
    </div>
  );
}

export default Footer;
