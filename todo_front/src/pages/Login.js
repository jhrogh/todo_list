import React from 'react';
import '../assets/styles/LoginStyle.css';
import { useNavigate } from 'react-router-dom';

function Login() {
  const navigate = useNavigate();

  const naviagteTo = path => {
    navigate(path);
  };

  return (
    <div className="login-container">
      <div className="login-body">
        <div className="login-title">회원 로그인</div>
        <div className="login-input">
          <input
            className="login-input-id"
            type="text"
            placeholder="아이디를 입력해주세요."
          />
          <input
            className="login-input-pw"
            type="password"
            placeholder="비밀번호를 입력해주세요."
          />
        </div>
        <button className="login-button" onClick={() => naviagteTo('/home')}>로그인</button>
        <div className="login-bottom">
          <div className="login-bottom-findid" onClick={() => naviagteTo('/find/id')}>아이디 찾기</div>
          <div className="login-bottom-border1">|</div>
          <div className="login-bottom-findpw" onClick={() => naviagteTo('find/pw')}>비밀번호 찾기</div>
          <div className="login-bottom-border2">|</div>
          <div className="login-bottom-join" onClick={() => naviagteTo('/join')}>회원가입</div>
        </div>
      </div>
    </div>
  );
}

export default Login;
