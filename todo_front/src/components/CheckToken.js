// checkToken.js
export const checkToken = async (navigate) => {
  try {
    const response = await fetch('/api/check-auth', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'include',
    });

    if (!response.ok) {
      throw new Error('Network response was not ok');
    }

    const data = await response.json();
    if (data.status === 'success') {
      return 'success';
    } else if (data.status === 'failed') {
      alert('재로그인 후 이용해주세요');
      navigate('/');
      return 'failed';
    } else if (data.status === 'error') {
      alert('로그인 후 이용 가능합니다.');
      navigate('/');
      return 'errord';
    } else {
      alert('알 수 없는 상태입니다.');
      navigate('/');
      return 'unknown';
    }
  } catch (error) {
    console.error('Error checking token:', error);
    alert('알 수 없는 상태입니다: error');
    navigate('/');
    return 'error';
  }
};