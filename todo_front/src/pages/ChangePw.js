import React, { useState } from 'react';
import '../assets/styles/ChangePwStyle.css';
import { useNavigate, useLocation } from 'react-router-dom';

function ChangePw() {
  const navigate = useNavigate();

  const location = useLocation();

  const [inputValues, setInputValues] = useState({
    password: '',
    confirmPw: '',
  });

  const handleKeyDown = e => {
    if (e.key === ' ' || e.key === 'Enter') {
      handleChangePw();
      e.preventDefault();
    }
  };

  const handleInput = e => {
    const { name, value } = e.target;
    setInputValues({ ...inputValues, [name]: value });
  };

  const queryParams = new URLSearchParams(location.search);
  const from = queryParams.get('from');
  const memberId = queryParams.get('memberId');

  const handleChangePw = async () => {
    if (inputValues.password !== inputValues.confirmPw) {
      alert('비밀번호가 일치하지 않습니다.');
      return;
    }
    const allFieldsFilled = Object.values(inputValues).every(
      value => value !== '',
    );
    if (!allFieldsFilled) {
      alert('모든 값을 입력해주세요.');
      return;
    }
    try {
      const response = await fetch(
        // 'http://43.202.173.195:8080/api/find/changepw',
        'http://localhost:8080/api/find/changepw',
        {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          credentials: 'include',
          body: JSON.stringify({
            password: inputValues.password,
            memberId: memberId,
          }),
        },
      );

      const data = await response.json();

      if (response.ok && data.status === 'success') {
        if (from === 'findpw') {
          alert('비밀번호가 변경되었습니다.');
          localStorage.removeItem('memberId');
          console.log(localStorage.getItem('memberId'));
          console.log(data.message);
          navigate('/');
        } else if (from === 'mypage') {
          console.log(data.message);
          console.log('mypage 이동');
          navigate('/mypage');
        }
      } else {
        alert('이미 사용중인 아이디 입니다.');
        setInputValues(prev => ({
          ...prev,
          memberId: '',
        }));
        document.querySelector('input[name="memberId"]').focus();
      }
    } catch (error) {
      console.log('Failed to Change Pw', error);
    }
  };

  return (
    <div className="changepw-container">
      <div className="changepw-body">
        <div className="changepw-title">비밀번호 설정</div>
        <div className="changepw-input">
          <label>*비밀번호</label>
          <input
            className="changepw-input-pw"
            type="password"
            name="password"
            value={inputValues.password}
            onKeyDown={handleKeyDown}
            onInput={handleInput}
          />
        </div>
        <div className="changepw-input">
          <label>*비밀번호 확인</label>
          <input
            className="changepw-input-pwcheck"
            type="password"
            name="confirmPw"
            value={inputValues.confirmPw}
            onKeyDown={handleKeyDown}
            onInput={handleInput}
          />
        </div>
        <button className="changepw-button" onClick={handleChangePw}>
          비밀번호 변경
        </button>
      </div>
    </div>
  );
}

export default ChangePw;
