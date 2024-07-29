import React from 'react';
import '../assets/styles/FindPwStyle.css';

function FindPw() {
  return (
    <div className="findpw-container">
      <div className="findpw-body">
        <div className="findpw-title">비밀번호 찾기</div>
        <div className="findpw-input">
          <label>*이메일</label>
          <input className="findpw-input-email" type="email" />
        </div>
        <div className="findpw-input">
          <label>*아이디</label>
          <input className="findpw-input-id" type="text" />
        </div>
        <button className="findpw-button">이메일 인증</button>
      </div>
    </div>
  );
}

export default FindPw;
