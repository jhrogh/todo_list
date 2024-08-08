import React, { useState, useEffect } from 'react';
import Nav from '../components/Nav';
import '../assets/styles/MypageStyle.css';
import { useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min';

// Modal.setAppElement('#root');

function Mypage() {
  const navigate = useNavigate();

  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [password, setPassword] = useState('');
  const openModal = () => {
    setModalIsOpen(true);
  };
  const closeModal = () => {
    setModalIsOpen(false);
    setPassword('');
  };

  const [isPageVisible, setIsPageVisible] = useState(false);
  const [userName, setUserName] = useState('');

  const [inputValues, setInputValues] = useState({
    memberId: '',
    name: '',
    email: '',
  });

  useEffect(() => {
    const showData = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/mypage/show', {
          method: 'GET',
          credentials: 'include',
        });
        if (response.ok) {
          const data = await response.json();
          const user = data.user;
          setInputValues({
            memberId: user.memberId,
            name: user.name,
            email: user.email,
          });
        } else {
          console.error('Failed to show mypage');
        }
      } catch (error) {
        console.log('Error', error);
      }
    };
    showData();
  }, []);

  const handleDeleteAccount = async () => {
    try {
      const response = await fetch(
        'http://localhost:8080/api/mypage/delete-account',
        {
          method: 'DELETE',
          headers: {
            'Content-Type': 'application/json',
          },
          credentials: 'include',
          body: JSON.stringify({ password }),
        },
      );

      const data = await response.json();

      if (response.ok && data.status === 'success') {
        console.log(data.message);
        alert('이용해주셔서 감사합니다');
        navigate('/');
      } else {
        if (data.status === 'failed') {
          console.log(data.message);
          alert('비밀번호를 다시 확인해주세요.');
        } else {
          console.log(data.message);
        }
      }
    } catch (error) {
      console.log('Failed to delete account', error);
    }
  };

  const handleChangePw = () => {
    const memberId = inputValues.memberId;
    const queryParams = new URLSearchParams({ from: 'mypage', memberId}).toString();
    const url = `/change/pw?${queryParams}`;
    navigate(url);
    // localStorage.setItem('memberId', inputValues.memberId);
    // navigate('/change/pw?from=mypage');
  };

  useEffect(() => {
    const modal = document.getElementById('deleteAccountModal');
    const handleHide = () => setPassword(''); // Clear password when modal is hidden

    modal?.addEventListener('hidden.bs.modal', handleHide);
    return () => {
      modal?.removeEventListener('hidden.bs.modal', handleHide);
    };
  }, []);

  return (
    <>
      <Nav setIsPageVisible={setIsPageVisible} setUserName={setUserName} />
      {isPageVisible && (
        <div className="mypage-container">
          <div className="mypage-body">
            <div className="mypage-title">마 이 페 이 지</div>
            <div className="mypage-input">
              <label>*아이디</label>
              <input
                className="mypage-input-id"
                type="text"
                value={inputValues.memberId}
                readOnly
              />
            </div>
            <div className="mypage-input">
              <label>*이름</label>
              <input
                className="mypage-input-name"
                type="text"
                value={inputValues.name}
                readOnly
              />
            </div>
            <div className="mypage-input">
              <label>*이메일</label>
              <input
                className="mypage-input-email"
                type="email"
                value={inputValues.email}
                readOnly
              />
            </div>
            {/* <div className="mypage-email">
          <button className="mypage-button-email">이메일 수정</button>
        </div> */}
            <div className="mypage-button">
              <button
                className="mypage-button-remove"
                data-bs-toggle="modal"
                data-bs-target="#deleteAccountModal"
              >
                회원 탈퇴
              </button>
              <button
                className="mypage-button-chagepw"
                onClick={handleChangePw}
              >
                비밀번호 변경
              </button>
            </div>
          </div>
        </div>
      )}
      {/* Modal */}
      <div
        className="modal fade"
        id="deleteAccountModal"
        tabIndex="-1"
        aria-labelledby="deleteAccountModalLabel"
        aria-hidden="true"
      >
        <div className="modal-dialog modal-dialog-centered">
          <div className="modal-content">
            <div className="modal-header">
              <h5 className="modal-title" id="deleteAccountModalLabel">
                회원 탈퇴
              </h5>
              <button
                type="button"
                className="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              ></button>
            </div>
            <div className="modal-body">
              회원 탈퇴를 위해서 비밀번호 입력이 필요합니다.
              <input
                type="password"
                className="form-control mt-3"
                value={password}
                onChange={e => setPassword(e.target.value)}
                placeholder="비밀번호"
              />
            </div>
            <div className="modal-footer">
              <button
                type="button"
                className="btn btn-secondary"
                data-bs-dismiss="modal"
              >
                취소
              </button>
              <button
                type="button"
                className="btn btn-primary"
                onClick={() => {
                  handleDeleteAccount();
                  document.querySelector('[data-bs-dismiss="modal"]').click(); // 모달 닫기
                }}
              >
                확인
              </button>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default Mypage;
