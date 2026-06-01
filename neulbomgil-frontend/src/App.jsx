import {Routes, Route, Link} from 'react-router-dom'
import Home from "./pages/Home.jsx";
import Map from "./pages/Map.jsx";
import BoardList from "./pages/BoardList.jsx";
import BoardEdit from "./pages/BoardEdit.jsx";
import BoardDetail from "./pages/BoardDetail.jsx";
import BoardWrite from "./pages/BoardWrite.jsx";
import MyPage from "./pages/MyPage.jsx";
import Login from './pages/login.jsx';
import Register from './pages/Register.jsx';
import MyPageChange from './pages/MyPageChange.jsx';
import {AuthProvider} from "./context/auth/AuthProvider.jsx";
// import Admin from "./pages/Admin.jsx";
// import Chat from './pages/Chat.jsx';


function App() {
    return (
        <AuthProvider>
            <nav className="flex gap-4">
                <Link to="/">홈</Link>
                <Link to="/map">지도</Link>
                <Link to="/boards">게시판</Link>
                <Link to="/my-page">마이페이지</Link>
                 <Link to="/admin">관리자페이지</Link>
            </nav>
            <Routes>
                <Route path="/" element={<Home/>}/>
                <Route path="/map" element={<Map/>}/>

                {/* 게시판 */}
                <Route path="/boards" element={<BoardList />} />
                <Route path="/boards/new" element={<BoardWrite />} />
                <Route path="/boards/:boardId" element={<BoardDetail />} />
                <Route path="/boards/:boardId/edit" element={<BoardEdit />} />

                <Route path="/my-page" element={<MyPage/>}/>
                <Route path="/my-page-change" element={<MyPageChange />}/>
                <Route path="/login" element={<Login />}/>
                <Route path="/signup" element={<Register />}/>
                {/* <Route path="/admin" element={<Admin/>}/>
                <Route path="/chatrooms/:roomId/message" element={<Chat />} /> */}
                <Route path="*" element={<div>페이지를 찾을 수 없습니다.</div>}/>
            </Routes>
        </AuthProvider>
    )
}

export default App