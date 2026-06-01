import { Routes, Route, useNavigate, useLocation } from "react-router-dom";
import Layout from "./components/common/Layout.jsx";

import Home from "./pages/Home.jsx";
import Map from "./pages/Map.jsx";
import BoardList from "./pages/BoardList.jsx";
import BoardEdit from "./pages/BoardEdit.jsx";
import BoardDetail from "./pages/BoardDetail.jsx";
import BoardWrite from "./pages/BoardWrite.jsx";
import MyPage from "./pages/MyPage.jsx";
import Admin from "./pages/Admin.jsx";
import Chat from "./pages/Chat.jsx";
import Login from "./pages/login.jsx";
import Register from "./pages/Register.jsx";
import MyPageChange from "./pages/MyPageChange.jsx";

function App() {
  const navigate = useNavigate();
 const location = useLocation();
  // 테스트용
  // 비로그인: "ANONYMOUS"
  // 일반회원: "USER"
  // 관리자: "ADMIN"
  const role = "ANONYMOUS";
const isMapPage = location.pathname === "/map";
  const handleLogout = () => {
    // 나중에 로그아웃 API 연결하면 여기에서 처리
    alert("로그아웃 되었습니다.");
    navigate("/");
  };

  return (
     <Layout role={role} onLogout={handleLogout} showFooter={!isMapPage}>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/map" element={<Map />} />

        {/* 게시판 */}
        <Route path="/boards" element={<BoardList />} />
        <Route path="/boards/new" element={<BoardWrite />} />
        <Route path="/boards/:boardId" element={<BoardDetail />} />
        <Route path="/boards/:boardId/edit" element={<BoardEdit />} />

        {/* 마이페이지 */}
        <Route path="/myPage" element={<MyPage />} />
        <Route path="/myPage-change" element={<MyPageChange />} />

        {/* 관리자 */}
        <Route path="/admin" element={<Admin />} />

        {/* 채팅 */}
        <Route path="/chatrooms/:roomId/message" element={<Chat />} />

        {/* 인증 */}
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Register />} />

        {/* 404 */}
        <Route path="*" element={<div>페이지를 찾을 수 없습니다.</div>} />
      </Routes>
    </Layout>
  );

}

export default App;