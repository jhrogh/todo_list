import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import '../assets/styles/NavStyle.css';

function Nav({ setIsPageVisible, setUserName }) {
  const navigate = useNavigate();
  const [userName, setUserNameState] = useState(''); // 사용자 이름 상태 추가

  useEffect(() => {
    const checkToken = async () => {
      try {
        const response = await fetch(
          // 'http://43.202.173.195:8080/api/check-auth',
          'http://localhost:8080/api/check-auth',
          {
            method: 'GET',
            headers: {
              'Content-Type': 'application/json',
            },
            credentials: 'include',
          },
        );

        const data = await response.json();

        if (response.ok) {
          if (data.status === 'success') {
            console.log(data.name);
            setIsPageVisible(true);
            setUserName(data.name);
            setUserNameState(data.name);
          } else if (data.status === 'failed') {
            console.log(data.message);
            handleTokenError();
          }
        } else {
          console.log(data.message);
          handleTokenError();
        }
      } catch (error) {
        console.error('Error checking token:', error);
        handleTokenError();
      }
    };

    const handleTokenError = () => {
      alert('재로그인 후 이용해주세요 :)');
      navigate('/');
      setIsPageVisible(false);
    };

    checkToken();
  }, [navigate, setIsPageVisible, setUserName]);

  const handleLogout = async () => {
    try {
      // const response = await fetch('http://43.202.173.195:8080/api/logout', {
        const response = await fetch('http://localhost:8080/api/logout', {
        method: 'GET',
        credentials: 'include',
      });
      const data = response.json();
      if (response.ok) {
        alert('로그아웃 되었습니다.');
        navigate('/');
      } else {
        alert('로그아웃 실패');
      }
    } catch (error) {
      console.log('Failed to delete account', error);
    }
  };

  return (
    <div className="nav-container">
      <div className="nav-logout" onClick={handleLogout}>
        logout
      </div>
      <div className="nav-useName">{userName} 님</div>
    </div>
  );
}

export default Nav;
