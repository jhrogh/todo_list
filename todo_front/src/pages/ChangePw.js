import React from 'react';
import '../assets/styles/ChangePwStyle.css';

function ChangePw() {
  return (
    <div className="changepw-container">
      <div className="changepw-body">
        <div className="changepw-title">비밀번호 설정</div>
        <div className="changepw-input">
          <label>*비밀번호</label>
          <input className="changepw-input-pw" type="password" />
        </div>
        <div className="changepw-input">
          <label>*비밀번호 확인</label>
          <input className="changepw-input-pwcheck" type="password" />
        </div>
        <button className="changepw-button">비밀번호 변경</button>
      </div>
    </div>
  );
}

export default ChangePw;
