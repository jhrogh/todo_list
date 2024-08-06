import React, { useState } from 'react';
import '../assets/styles/FindPwStyle.css';
import { useNavigate } from 'react-router-dom';

function FindPw() {
  const navigate = useNavigate();
  const [sendCode, setSendCode] = useState(false);
  const [inputValues, setInputValues] = useState({
    email: '',
    memberId: '',
    emailCode: '',
  });

  const handleInput = e => {
    const { name, value } = e.target;
    setInputValues({ ...inputValues, [name]: value });
  };

  const handleEmailVerification = async () => {
    if (inputValues.email == '' || inputValues.memberId == '') {
      alert('모든 값을 입력해주세요.');
      return;
    }

    try {
      const response = await fetch(
        'https://localhost:8443/api/find/pw/verify-email',
        {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            email: inputValues.email,
            memberId: inputValues.memberId,
          }),
        },
      );

      const data = await response.json();

      if (response.ok && data.status === 'success') {
        alert('이메일 인증 코드가 전송되었습니다.');
        setSendCode(true);
      } else {
        alert('가입정보가 없는 회원입니다. 회원가입 후 이용해주세요 :)');
      }
    } catch (error) {
      console.error('Failed to Email Verification', error);
    }
  };

  const handleEmailCode = async () => {
    const allFieldsFilled = Object.values(inputValues).every(
      value => value !== '',
    );
    if (!allFieldsFilled) {
      alert('모든 값을 입력해주세요.');
      return;
    }

    try {
      const response = await fetch(
        `https://localhost:8443/api/find/pw/verify-email/code?code=${encodeURIComponent(
          inputValues.emailCode,
        )}`,
      );

      if (response.ok) {
        alert('인증이 완료되었습니다.');
        localStorage.setItem('memberId', inputValues.memberId);
        navigate('/change/pw');
      } else {
        alert('인증코드가 틀립니다.');
        setInputValues(prev => ({
          ...prev,
          emailCode: '',
        }));
        document.querySelector('input[name="emailCode"]').focus();
      }
    } catch (error) {
      console.error('Failed to Email Verification', error);
    }
  };

  return (
    <div className="findpw-container">
      <div className="findpw-body">
        <div className="findpw-title">비밀번호 찾기</div>
        <div className="findpw-input">
          <label>*이메일</label>
          <input
            className="findpw-input-email"
            type="email"
            name="email"
            value={inputValues.email}
            onInput={handleInput}
          />
        </div>
        <div className="findpw-input">
          <label>*아이디</label>
          <input
            className="findpw-input-id"
            type="text"
            name="memberId"
            value={inputValues.memberId}
            onInput={handleInput}
          />
        </div>
        {sendCode && (
          <div className="findpw-input">
            <label></label>
            <input
              className="findpw-input-email-code"
              type="text"
              name="emailCode"
              placeholder="인증번호"
              value={inputValues.emailCode}
              onInput={handleInput}
            />
          </div>
        )}
        {!sendCode && (
          <button className="findpw-button" onClick={handleEmailVerification}>
            이메일 인증
          </button>
        )}
        {sendCode && (
          <button
            className="findpw-button"
            onClick={handleEmailCode}
          >
            인증번호 확인
          </button>
        )}
        <div className="login-bottom-menu">
          <div
            className="login-bottom-findid"
            onClick={() => navigate('/')}
          >
            로그인하기
          </div>
          <div className="login-bottom-border1">|</div>
          <div
            className="login-bottom-findpw"
            onClick={() => navigate('/find/id')}
          >
            아이디 찾기
          </div>
          <div className="login-bottom-border2">|</div>
          <div
            className="login-bottom-join"
            onClick={() => navigate('/join')}
          >
            회원가입
          </div>
        </div>
      </div>
      
    </div>
  );
}

export default FindPw;
