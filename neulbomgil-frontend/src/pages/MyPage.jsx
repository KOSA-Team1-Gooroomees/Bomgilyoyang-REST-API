import styles from "./mypage-theme.css";

export default function MyPage() {
    return (
        <>
            <main className="max-w-6xl mx-auto py-12 px-4">

                <div className="mb-8">
                    <h1 className="text-2xl font-bold mb-2">마이페이지</h1>
                    <p className="text-sm text-gray-600">내 정보와 활동을 한눈에 확인해보세요</p>
                </div>

                <div className="bg-[#e6eee8] rounded-2xl p-8 flex items-center justify-between mb-12 shadow-sm">
                    <div className="flex items-center gap-6">
                        <div>
                            <h2 className="text-3xl font-bold text-gray-900 mb-2">
                                안녕하세요 <span th:text="${user.name}">000</span> 님
                            </h2>
                            <p className="text-lg text-gray-500 font-medium"
                               th:text="${user.email}">kim123@naver.com</p>
                        </div>
                    </div>
                    <button className="text-gray-500 hover:text-gray-700 transition-colors">
                        <i className="fa-solid fa-gear text-4xl"></i>
                    </button>
                </div>

                <div className="grid grid-cols-1 md:grid-cols-2 gap-8">

                    <div
                        className="bg-white border-2 border-gray-200 rounded-3xl p-6 shadow-sm h-[600px] flex flex-col">
                        <div className="mb-6">
                            <h3 className="text-xl font-bold mb-1">즐겨찾는 시설</h3>
                            <p className="text-sm text-gray-500">자주 찾는 시설을 한눈에 확인하세요.</p>
                        </div>

                        <div className="flex-1 overflow-y-auto space-y-4 pr-2 custom-scrollbar">

                            <div th:each="facility : ${favoriteFacilities}"
                                 className="bg-white border border-gray-200 rounded-2xl p-4 flex gap-4 relative shadow-sm hover:shadow-md transition-shadow">
                                <div
                                    className="w-20 h-20 bg-blue-50 rounded-xl flex items-center justify-center text-blue-400">
                                    <i className="fa-solid fa-house-medical text-3xl"></i>
                                </div>

                                <div className="flex-1 py-1">
                                    <div className="flex justify-between items-start mb-1">
                                        <h4 className="font-bold text-gray-800 text-lg"
                                            th:text="${facility.name}">함열덕성원</h4>
                                        <span
                                            className="text-xs font-bold text-blue-500 bg-blue-50 px-2 py-1 rounded-md"
                                            th:text="${facility.distance} + 'km'">159.5km</span>
                                    </div>

                                    <div className="text-[#5CB071] text-xs mb-3 flex gap-1">
                                        <i className="fa-solid fa-tree"></i><i className="fa-solid fa-tree"></i><i
                                        className="fa-solid fa-tree"></i><i className="fa-solid fa-tree"></i><i
                                        className="fa-solid fa-tree"></i>
                                    </div>

                                    <div className="text-xs text-gray-500 space-y-1.5">
                                        <p className="flex items-center gap-1.5">
                                            <i className="fa-solid fa-location-dot text-red-500 w-3"></i>
                                            <span th:text="${facility.address}">전라북도 익산시 익산대로 1666-33</span>
                                        </p>
                                        <p className="flex items-center gap-1.5">
                                            <i className="fa-solid fa-phone text-gray-400 w-3"></i>
                                            <span th:text="${facility.phone}">063-862-8882</span>
                                        </p>
                                    </div>
                                </div>

                                <button
                                    className="absolute top-4 right-1/2 translate-x-[40px] text-red-500 hover:text-red-600">
                                    <i className="fa-solid fa-heart text-2xl drop-shadow-sm"></i>
                                </button>
                            </div>

                        </div>
                    </div>

                    <div
                        className="bg-white border-2 border-gray-200 rounded-3xl p-6 shadow-sm h-[600px] flex flex-col">
                        <div className="mb-6">
                            <h3 className="text-xl font-bold mb-1">내가 쓴 게시판</h3>
                            <p className="text-sm text-gray-500">내가 작성한 게시판을 확인해보세요.</p>
                        </div>

                        <div className="flex-1 overflow-y-auto space-y-4 pr-2 custom-scrollbar">

                            <div th:each="post : ${myPosts}"
                                 className="bg-white border border-gray-200 rounded-2xl p-4 flex gap-4 relative shadow-sm hover:shadow-md transition-shadow">
                                <div
                                    className="w-20 h-20 bg-blue-50 rounded-xl flex items-center justify-center text-blue-400">
                                    <i className="fa-regular fa-file-lines text-3xl"></i>
                                </div>

                                <div className="flex-1 py-1">
                                    <div className="flex justify-between items-start mb-1">
                                        <h4 className="font-bold text-gray-800 text-lg" th:text="${post.title}">함열덕성원
                                            리뷰</h4>
                                        <span className="text-xs text-gray-400"
                                              th:text="${#temporals.format(post.createdAt, 'yyyy-MM-dd')}">2026-05-27</span>
                                    </div>

                                    <div className="text-yellow-400 text-xs mb-3 flex gap-1">
                                        <i className="fa-solid fa-star"></i><i className="fa-solid fa-star"></i><i
                                        className="fa-solid fa-star"></i><i className="fa-solid fa-star"></i><i
                                        className="fa-regular fa-star"></i>
                                    </div>

                                    <div className="text-xs text-gray-500 line-clamp-2" th:text="${post.content}">
                                        시설이 아주 깨끗하고 직원분들이 친절하십니다. 추천합니다.
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>

                </div>
            </main>
        </>
    )
}