import React, { useState } from 'react';
import '../assets/styles/LoginStyle.css';
import { useNavigate } from 'react-router-dom';

function Login() {
  const navigate = useNavigate();

  const navigateTo = path => {
    navigate(path);
  };

  const handleKeyDown = e => {
    if (e.key === '') {
      e.preventDefault();
    }
  };

  const [inputValues, setInputValues] = useState({
    memberId: '',
    password: '',
  });
  
  const [error, setError] = useState({ memberId: '', password: '' });

  const handleInput = e => {
    const { name, value } = e.target; // const name = e.target.name; const value = e.target.value;
    let processedValue = value;
    let errorMessage = '';

    if (name === 'memberId') {
      if (value.length > 12) {
        processedValue = value.slice(0, 12);
        errorMessage = '최대 12자 입력 가능';
      } else {
        const memberIdRegex = /^(?=.*[a-z])(?=.*\d)[a-z\d]{0,12}$/;
        if (!memberIdRegex.test(value)) {
          errorMessage = '영어와 숫자 필수, 최대 12자 입력 가능';
        }
      }
    } else if (name === 'password') {
      if (value.length > 12) {
        processedValue = value.slice(0, 12);
        errorMessage = '8자 이상 12자 이하 입력 가능';
      } else {
        const passwordRegex =
          /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d!@#$%^&*()_+=-]{8,12}$/;
        if (processedValue.length < 8) {
          errorMessage = '8자 이상 12자 이하 입력 가능';
        } else if (!passwordRegex.test(processedValue)) {
          errorMessage = '영어와 숫자 필수, 특수문자는 선택 입력';
        }
      }
    }
    setInputValues({ ...inputValues, [name]: processedValue });
    setError({ ...error, [name]: errorMessage });
  };

  return (
    <div className="login-container">
      <div className="login-body">
        <div className="login-title">회원 로그인</div>
        <div className="login-input">
          <input
            className="login-input-id"
            type="text"
            name="memberId"
            placeholder="아이디를 입력해주세요."
            value={inputValues.memberId}
            onKeyDown={handleKeyDown}
            onInput={handleInput}
          />
          {error.memberId && <p className="error-message">{error.memberId}</p>}
          <input
            className="login-input-pw"
            type="password"
            name="password"
            placeholder="비밀번호를 입력해주세요."
            value={inputValues.password}
            onKeyDown={handleKeyDown}
            onInput={handleInput}
          />
          {error.password && <p className="error-message">{error.password}</p>}
        </div>
        <button className="login-button" onClick={() => navigateTo('/home')}>
          로그인
        </button>
        <div className="login-bottom-menu">
          <div
            className="login-bottom-findid"
            onClick={() => navigateTo('/find/id')}
          >
            아이디 찾기
          </div>
          <div className="login-bottom-border1">|</div>
          <div
            className="login-bottom-findpw"
            onClick={() => navigateTo('find/pw')}
          >
            비밀번호 찾기
          </div>
          <div className="login-bottom-border2">|</div>
          <div
            className="login-bottom-join"
            onClick={() => navigateTo('/join')}
          >
            회원가입
          </div>
        </div>
      </div>
    </div>
  );
}

export default Login;
