import React, { useState } from 'react';
import '../assets/styles/FindIdStyle.css';
import { useNavigate } from 'react-router-dom';

function FindId() {
  const navigate = useNavigate();

  const [inputValues, setInputValues] = useState({ name: '', email: '' });

  const handleKeyDown = e => {
    if (e.key === ' ' || e.key === 'Enter') {
      handleFindId();
      e.preventDefault();
    }
  };

  const handleInput = e => {
    const { name, value } = e.target;
    setInputValues({ ...inputValues, [name]: value });
  };

  const handleFindId = async () => {
    const allFieldsFilled = Object.values(inputValues).every(
      value => value !== '',
    );
    if (!allFieldsFilled) {
      alert('모든 값을 입력해주세요.');
      return;
    }
    try {
      const response = await fetch('http://43.202.173.195:8080/api/find/id', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
        body: JSON.stringify({
          name: inputValues.name,
          email: inputValues.email,
        }),
      });

      const data = await response.json();

      if (response.ok && data.status === 'success') {
        console.log(data.message);
        alert(`아이디는 ${data.memberId} 입니다.`);
        navigate('/');
      } else if (data.status === 'failed') {
        console.log(data.message);
        alert('이메일이 일치하지 않습니다.');
      } else if (data.status === 'Not Found') {
        console.log(data.message);
        alert('가입정보가 없는 회원입니다. 회원가입 후 이용해주세요 :)');
      }
    } catch (error) {
      console.error('Failed to Find Id', error);
    }
  };

  return (
    <div className="findid-container">
      <div className="findid-body">
        <div className="findid-title">아이디 찾기</div>
        <div className="findid-input">
          <label>*이름</label>
          <input
            className="findid-input-name"
            type="text"
            name="name"
            value={inputValues.name}
            onKeyDown={handleKeyDown}
            onInput={handleInput}
          />
        </div>
        <div className="findid-input">
          <label>*이메일</label>
          <input
            className="findid-input-email"
            type="email"
            name="email"
            value={inputValues.email}
            onKeyDown={handleKeyDown}
            onInput={handleInput}
          />
        </div>
        <button className="findid-button" onClick={handleFindId}>
          찾기
        </button>

        <div className="login-bottom-menu">
          <div className="login-bottom-findid" onClick={() => navigate('/')}>
            로그인하기
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

export default FindId;
