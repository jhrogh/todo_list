import React from 'react';
import Nav from '../components/Nav';
import '../assets/styles/MypageStyle.css';
import { useNavigate } from 'react-router-dom';

function Mypage() {
  const navigate = useNavigate();

  const navigateTo = path => {
    navigate(path);
  };

  return (
    <>
    <Nav />
    <div className="mypage-container">
      <div className="mypage-body">
        <div className="mypage-title">마 이 페 이 지</div>
        <div className="mypage-input">
          <label>*아이디</label>
          <input className="mypage-input-id" type="text" />
        </div>
        <div className="mypage-input">
          <label>*이름</label>
          <input className="mypage-input-name" type="text" />
        </div>
        <div className="mypage-input">
          <label>*이메일</label>
          <input className="mypage-input-email" type="email" />
        </div>
        <div className="mypage-email">
          <button className="mypage-button-email">이메일 수정</button>
        </div>
        <div className="mypage-button">
          <button className="mypage-button-remove">회원 탈퇴</button>
          <button className="mypage-button-chagepw" onClick={() => navigateTo('/change/pw')}>비밀번호 변경</button>
        </div>
      </div>
    </div>
    </>
  );
}

export default Mypage;
