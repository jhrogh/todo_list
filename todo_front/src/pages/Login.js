import React, { useState } from 'react';
import '../assets/styles/LoginStyle.css';
import { useNavigate } from 'react-router-dom';

function Login() {
  const navigate = useNavigate();

  const handleKeyDown = e => {
    if (e.key === ' ') {
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

    if (value === '') {
      setError({ ...error, [name]: '' });
    } else {
      // 아이디 유효성 검사
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
      }
      // 비밀번호 유효성 검사
      else if (name === 'password') {
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
      setError({ ...error, [name]: errorMessage });
    }
    setInputValues({ ...inputValues, [name]: processedValue });
  };

  // 로그인하기 버튼
  const handleLogin = async () => {
    const allFieldsFilled = Object.values(inputValues).every(
      value => value !== '',
    );
    const noErrors = Object.values(error).every(errorMsg => errorMsg === '');

    if (!allFieldsFilled) {
      alert('모든 값을 입력해주세요.');
      return;
    }

    if (!noErrors) {
      alert('입력한 내용을 다시 확인해주세요.');
      return;
    }

    try {
      const response = await fetch('https://localhost:8443/api/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
        body: JSON.stringify({
          memberId: inputValues.memberId,
          password: inputValues.password,
        }),
      });

      const data = await response.json();

      if (response.ok && data.status === 'success') {
        navigate('/home');
      } else {
        if (data.status === 'error' && data.message === 'Failed Password') {
          alert('비밀번호가 일치하지 않습니다.');
        } else if (data.status === 'error' && data.message === 'Not Found Id') {
          alert('가입된 아이디가 아닙니다. 회원가입 후 이용해주세요 :)');
        }
      }
    } catch (error) {
      console.error('Failed to login', error);
    }
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
        <button className="login-button" onClick={handleLogin}>
          로그인
        </button>
        <div className="login-bottom-menu">
          <div
            className="login-bottom-findid"
            onClick={() => navigate('/find/id')}
          >
            아이디 찾기
          </div>
          <div className="login-bottom-border1">|</div>
          <div
            className="login-bottom-findpw"
            onClick={() => navigate('/find/pw')}
          >
            비밀번호 찾기
          </div>
          <div className="login-bottom-border2">|</div>
          <div className="login-bottom-join" onClick={() => navigate('/join')}>
            회원가입
          </div>
        </div>
      </div>
    </div>
  );
}

export default Login;
