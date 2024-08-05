import React, { useState } from 'react';
import '../assets/styles/ChangePwStyle.css';
import { useNavigate } from 'react-router-dom';

function ChangePw() {
  const navigate = useNavigate();
  const [inputValues, setInputValues] = useState({password: "", confirmPw: ""});

  const handleInput = e => {
    const { name, value } = e.target;
    setInputValues({ ...inputValues, [name]: value });
  }

  const handleChangePw = async () => {
    if(inputValues.password !== inputValues.confirmPw) {
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
      const response = await fetch('https://localhost:8443/api/find/changepw', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          password: inputValues.password,
          confirmPw: inputValues.confirmPw,
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
    } catch(error) {
      console.log('Failed to Change Pw', error);
    }
  }

  return (
    <div className="changepw-container">
      <div className="changepw-body">
        <div className="changepw-title">비밀번호 설정</div>
        <div className="changepw-input">
          <label>*비밀번호</label>
          <input className="changepw-input-pw" type="password" name="password" value={inputValues.password} onInput={handleInput} />
        </div>
        <div className="changepw-input">
          <label>*비밀번호 확인</label>
          <input className="changepw-input-pwcheck" type="password" name="confirmPw" value={inputValues.confirmPw} onInput={handleInput} />
        </div>
        <button className="changepw-button" onClick={handleChangePw}>비밀번호 변경</button>
      </div>
    </div>
  );
}

export default ChangePw;
