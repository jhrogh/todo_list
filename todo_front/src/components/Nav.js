import Reac, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import '../assets/styles/NavStyle.css';

function Nav({ setIsPageVisible, setUserName }) {
  const navigate = useNavigate();
  const [userName, setUserNameState] = useState(''); // 사용자 이름 상태 추가

  useEffect(() => {
    const checkToken = async () => {
      try {
        const response = await fetch('https://localhost:8443/api/check-auth', {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },
          credentials: 'include',
        });

        if (response.ok) {
          const data = await response.json();
          if (data.status === 'success') {
            console.log('토큰 확인');
            setIsPageVisible(true);
            setUserName(data.name);
            setUserNameState(data.name);
          } else {
            handleTokenError();
          }
        } else {
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

  return <div className="nav-container">{userName}</div>;
}

export default Nav;
