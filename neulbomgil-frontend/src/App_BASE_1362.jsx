import {Routes, Route, Link} from 'react-router-dom'
import Home from "./pages/Home.jsx";
import Map from "./pages/Map.jsx";
import Board from "./pages/Board.jsx";
import MyPage from "./pages/MyPage.jsx";
import FavoriteTest from "./pages/FavoriteTest.jsx";

function App() {
    return (
        <div>
            <nav className="flex gap-4">
                <Link to="/">홈</Link>
                <Link to="/map">지도</Link>
                <Link to="/favorite">즐겨찾기</Link>
                <Link to="/board">게시판</Link>
                <Link to="/myPage">마이페이지</Link>
            </nav>
            <Routes>
                <Route path="/" element={<Home/>}/>
                <Route path="/map" element={<Map/>}/>
                <Route path="/favorite" element={<FavoriteTest/>}/>
                <Route path="/board" element={<Board/>}/>
                <Route path="/myPage" element={<MyPage/>}/>
                <Route path="*" element={<div>페이지를 찾을 수 없습니다.</div>}/>
            </Routes>
        </div>
    )
}

export default App