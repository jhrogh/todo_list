import React from 'react';
import '../assets/styles/LoginStyle.css'

function Login() {
  return (
    <div className="login-container">
      <div className="login-title">회원 로그인</div>
      <div className='login-input'>
        <input className='login-input-id' type='text' placeholder='아이디를 입력해주세요.' />
        <input className='login-input-pw' type='password' placeholder='비밀번호를 입력해주세요.' />
      </div>
      <button className='login-button'>로그인</button>
      <div className='login-bottom'>
        <div className='login-bottom-findid'>아이디 찾기</div>
        <div className='login-bottom-border1'>|</div>
        <div className='login-bottom-findpw'>비밀번호 찾기</div>
        <div className='login-bottom-border2'>|</div>
        <div className='login-bottom-join'>회원가입</div>
      </div>
    </div>
  );
}

export default Login;
