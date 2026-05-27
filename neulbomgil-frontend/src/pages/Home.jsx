import {useState} from 'react';
import api from '../api/axios.js';

const Home = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const params = new URLSearchParams();
            params.append('email', email);
            params.append('password', password);
            const response = await api.post('/api/auth/login', params, {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            });
            const token = response.data.accessToken
            if (token) {
                localStorage.setItem('accessToken', token);
                alert('로그인 성공!');
            } else {
                alert('로그인에 성공했으나 토큰을 찾을 수 없습니다.');
            }

        } catch (err) {
            console.error('로그인 에러:', err);
            alert('로그인 실패');
        }
    };

    return (
        <div style={{padding: '20px'}}>
            <form onSubmit={handleLogin}>
                <div style={{marginBottom: '10px'}}>
                    <input
                        type="email"
                        placeholder="이메일"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        style={{padding: '8px', width: '200px'}}
                    />
                </div>
                <div style={{marginBottom: '10px'}}>
                    <input
                        type="password"
                        placeholder="비밀번호"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        style={{padding: '8px', width: '200px'}}
                    />
                </div>
                <button type="submit" style={{padding: '8px 16px'}}>
                    로그인
                </button>
            </form>
        </div>
    );
};

export default Home;