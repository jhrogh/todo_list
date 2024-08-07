import React, { useState } from 'react';
import '../assets/styles/JoinStyle.css';
import { useNavigate } from 'react-router-dom';

function Join() {
  const navigate = useNavigate();

  const handleKeyDown = e => {
    if (e.key === ' ' || e.key === 'Enter') {
      handleSubmit();
      e.preventDefault();
    }
  };

  const [inputValues, setInputValues] = useState({
    memberId: '',
    name: '',
    password: '',
    confirmPassword: '',
    email: '',
    emailCode: '',
  });

  const [error, setError] = useState({
    memberId: '',
    name: '',
    password: '',
    confirmPassword: '',
    email: '',
    emailCode: '',
  });

  // 아이디 유효성 검사
  const handleInputId = e => {
    const { name, value } = e.target;
    let processedValue = value;
    let errorMessage = '';

    if (value === '') {
      setError({ ...error, [name]: '' });
    } else {
      if (value.length > 12) {
        processedValue = value.slice(0, 12);
        errorMessage = '최대 12자 입력 가능';
      } else {
        const memberIdRegex = /^(?=.*[a-z])(?=.*\d)[a-z\d]{0,12}$/;
        if (!memberIdRegex.test(value)) {
          errorMessage = '영어와 숫자 필수, 최대 12자 입력 가능';
        }
      }
      setError({ ...error, [name]: errorMessage });
    }
    setInputValues({ ...inputValues, [name]: processedValue });
  };

  // 이름 유효성 검사
  const handleInputName = e => {
    const { name, value } = e.target;
    let processedValue = value;
    let errorMessage = '';

    if (value === '') {
      setError({ ...error, [name]: '' });
    } else {
      if (value.length < 2) {
        errorMessage = '최소 2자 이상 가능';
      } else {
        const nameRegex = /^[a-zA-Zㄱ-ㅎ가-힣]{0,12}$/;
        if (!nameRegex.test(value)) {
          errorMessage = '한글 또는 영어만 입력 가능, 최대 12자 입력 가능';
        }
      }
      setError({ ...error, [name]: errorMessage });
    }
    setInputValues({ ...inputValues, [name]: processedValue });
  };

  // 비밀번호 유효성 검사
  const handleInputPw = e => {
    const { name, value } = e.target;
    let processedValue = value;
    let errorMessage = '';

    if (value === '') {
      setError({ ...error, [name]: '' });
    } else {
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
      setError({ ...error, [name]: errorMessage });
    }
    setInputValues({ ...inputValues, [name]: processedValue });
  };

  // 비밀번호 재확인
  const handleConfirmPw = e => {
    const { name, value } = e.target;
    let processedValue = value;
    let errorMessage = '';

    if (value === '') {
      setError({ ...error, [name]: '' });
    } else {
      if (value !== inputValues.password) {
        errorMessage = '입력한 비밀번호와 다릅니다.';
      } else {
        errorMessage = '';
      }
    }
    setError({ ...error, [name]: errorMessage });
    setInputValues({ ...inputValues, [name]: processedValue });
  };

  // 이메일 유효성 검사
  const handleInputEmail = e => {
    const { name, value } = e.target;
    let processedValue = value;
    let errorMessage = '';

    if (value === '') {
      setError({ ...error, [name]: '' });
    } else {
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!emailRegex.test(value)) {
        errorMessage = '유효한 이메일 주소를 입력해주세요.';
      }
      setError({ ...error, [name]: errorMessage });
    }
    setInputValues({ ...inputValues, [name]: processedValue });
  };

  // 이메일 인증코드 유효성 검사
  const handleInputEmailCode = e => {
    const { name, value } = e.target;
    let processedValue = value;

    if (value === '') {
      setError({ ...error, [name]: '' });
    }
    setInputValues({ ...inputValues, [name]: processedValue });
  };

  // 이메일 인증 버튼
  const [emailVerified, setEmailVerified] = useState(false);
  const [sendCode, setSendCode] = useState(false); // 추가

  const handleEmailVerification = async () => {
    if (inputValues.email === '') {
      alert('이메일을 입력해주세요.');
      return;
    }

    try {
      const response = await fetch(
        'https://localhost:8443/api/join/verify-email',
        {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          credentials: 'include',
          body: JSON.stringify({ email: inputValues.email }),
        },
      );

      const data = await response.json();

      if (response.ok && data.status === 'success') {
        alert('이메일 인증 코드가 전송되었습니다.');
        setSendCode(true);
      } else {
        alert('이메일 인증에 실패했습니다.');
      }
    } catch (error) {
      console.error('Failed to send email verification', error);
    }
  };

  const handleEmailCode = async () => {
    if (inputValues.emailCode === '') {
      alert('인증코드를 정확히 입력해주세요.');
      return;
    }

    try {
      const response = await fetch(
        `https://localhost:8443/api/join/verify-email/code?code=${encodeURIComponent(
          inputValues.emailCode,
        )}`,{
          method: 'GET', 
          headers: {
            'Content-Type': 'application/json',
          },
          credentials: 'include', 
        }
      );

      if (response.ok) {
        alert('인증이 완료되었습니다.');
        setEmailVerified(true);
      } else {
        alert('인증코드가 틀립니다.');
        setInputValues(prev => ({
          ...prev,
          emailCode: '',
        }));
        document.querySelector('input[name="emailCode"]').focus();
      }
    } catch (error) {
      console.error('Failed to email code', error);
    }
  };

  // 가입하기 버튼
  const handleSubmit = async () => {
    const allFieldsFilled = Object.values(inputValues).every(
      value => value !== '',
    );
    const noErrors = Object.values(error).every(errorMsg => errorMsg === '');
    const emailVerificationComplete = emailVerified;

    if (!allFieldsFilled) {
      alert('모든 값을 입력해주세요.');
      return;
    }

    if (!noErrors) {
      alert('입력한 내용을 다시 확인해주세요.');
      return;
    }

    if (!emailVerificationComplete) {
      alert('이메일 인증을 진행해주세요.');
      return;
    }

    try {
      const response = await fetch('https://localhost:8443/api/join', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
        body: JSON.stringify({
          memberId: inputValues.memberId,
          name: inputValues.name,
          password: inputValues.password,
          email: inputValues.email,
          code: inputValues.emailCode,
        }),
      });

      const data = await response.json();

      if (response.ok && data.status === 'success') {
        alert('회원가입이 완료되었습니다. 로그인 후 이용해주세요 :)');
        navigate('/');
      } else {
        alert('이미 사용중인 아이디 입니다.');
        setInputValues(prev => ({
          ...prev,
          memberId: '',
        }));
        document.querySelector('input[name="memberId"]').focus();
      }
    } catch (error) {
      console.error('Failed to sign up', error);
    }
  };

  return (
    <div className="join-container">
      <div className="join-body">
        <div className="join-title">회 원 가 입</div>
        <div className="join-input">
          <div className="join-input-group">
            <label>*아이디</label>
            <input
              className="join-input-id"
              type="text"
              name="memberId"
              placeholder="아이디를 입력해주세요."
              value={inputValues.memberId}
              onKeyDown={handleKeyDown}
              onInput={handleInputId}
            />
          </div>
          {error.memberId && <p className="error-message">{error.memberId}</p>}
        </div>

        <div className="join-input">
          <div className="join-input-group">
            <label>*이름</label>
            <input
              className="join-input-name"
              type="text"
              name="name"
              value={inputValues.name}
              onKeyDown={handleKeyDown}
              onInput={handleInputName}
            />
          </div>
          {error.name && <p className="error-message">{error.name}</p>}
        </div>
        <div className="join-input">
          <div className="join-input-group">
            <label>*비밀번호</label>
            <input
              className="join-input-pw"
              type="password"
              name="password"
              placeholder="비밀번호를 입력해주세요."
              value={inputValues.password}
              onKeyDown={handleKeyDown}
              onInput={handleInputPw}
            />
          </div>
          {error.password && <p className="error-message">{error.password}</p>}
        </div>
        <div className="join-input">
          <div className="join-input-group">
            <label>*비밀번호 확인</label>
            <input
              className="join-input-pwcheck"
              type="password"
              name="confirmPassword"
              placeholder="비밀번호를 다시 입력해주세요."
              value={inputValues.confirmPassword}
              onKeyDown={handleKeyDown}
              onInput={handleConfirmPw}
            />
          </div>
          {error.confirmPassword && (
            <p className="error-message">{error.confirmPassword}</p>
          )}
        </div>
        <div className="join-input">
          <div className="join-input-group">
            <label>*이메일</label>
            <input
              className="join-input-email"
              type="email"
              name="email"
              value={inputValues.email}
              onKeyDown={handleKeyDown}
              onInput={handleInputEmail}
              readOnly={emailVerified}
            />
          </div>
          {error.email && <p className="error-message">{error.email}</p>}
        </div>
        {!sendCode && (
          <div className="join-email">
            <button
              className="join-button-email"
              onClick={handleEmailVerification}
              disabled={emailVerified}
              style={{ backgroundColor: emailVerified ? '#c9c9c9' : '#52b6ff' }}
            >
              인증하기
            </button>
          </div>
        )}
        {sendCode && (
          <div className="join-input">
            <div className="join-input-group">
              <label></label>
              <input
                className="join-input-email-code"
                type="text"
                name="emailCode"
                placeholder="인증번호"
                value={inputValues.emailCode}
                onKeyDown={handleKeyDown}
                onInput={handleInputEmailCode}
                readOnly={emailVerified}
              />
            </div>
            {error.emailCode && (
              <p className="error-message">{error.emailCode}</p>
            )}
          </div>
        )}
        {sendCode && (
          <div className="join-email">
            <button
              className="join-button-email"
              onClick={handleEmailCode}
              disabled={emailVerified}
              style={{ backgroundColor: emailVerified ? '#c9c9c9' : '#52b6ff' }}
            >
              코드 확인
            </button>
          </div>
        )}
        <button className="join-button" onClick={handleSubmit}>
          가입하기
        </button>
        <div className="login-bottom-menu">
          <div
            className="login-bottom-findid"
            onClick={() => navigate('/')}
          >
            로그인하기
          </div>          
        </div>
      </div>
    </div>
  );
}

export default Join;
