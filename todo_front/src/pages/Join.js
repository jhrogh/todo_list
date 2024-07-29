import React from 'react';
import '../assets/styles/JoinStyle.css';

function Join() {
  return (
    <div className="join-container">
      <div className='join-body'>
        <div className="join-title">회원가입</div>
        <div className="join-input">
          <label>*아이디</label>
          <input
            className="join-input-id"
            type="text"
            placeholder="아이디를 입력해주세요."
          />
        </div>
        <div className="join-input">
          <label>*이름</label>
          <input className="join-input-name" type="text" />
        </div>
        <div className="join-input">
          <label>*비밀번호</label>
          <input
            className="join-input-pw"
            type="password"
            placeholder="비밀번호를 입력해주세요."
          />
        </div>
        <div className="join-input">
          <label>*비밀번호 확인</label>
          <input
            className="join-input-pwcheck"
            type="password"
            placeholder="비밀번호를 다시 입력해주세요."
          />
        </div>
        <div className="join-input">
          <label>*이메일</label>
          <input className="join-input-email" type="email" />
        </div>
        <div className='join-email'>
          <button className='join-button-email'>인증하기</button>
        </div>        
        <button className="join-button">가입하기</button>
      </div>
    </div>
  );
}

export default Join;
