import './Login.css';
import LoginModal from "../components/auth/LoginModal";

export default function Login() {
    return (
        <>
            <div className="bg-gradient-to-b from-[#E0F0E9] to-[#C9E4D6] min-h-screen text-gray-800 flex flex-col">

                <div className="flex-1 flex items-center justify-center relative overflow-hidden px-4 py-10">

                    <div className="relative z-10 w-full max-w-[400px] px-5 mt-[-50px] ">

                        <div
                            className="rounded-[20px] shadow-lg p-6 relative z-10 mt-10 bg-[url('/images/login-background.png')] bg-cover bg-bottom ">
                            <div className="text-center relative mb-6 ">

                                <img src="/images/characters/rumi-left.png" alt="봄길요양 캐릭터"
                                    className="absolute -top-6 -right-10 w-28 h-auto z-20" />

                                    <h1 className="mt-10 text-3xl font-bold text-[#1F3D2D] mb-2 tracking-tight">
                                        로그인
                                    </h1>
                                    <p className="text-gray-500 text-sm">봄길요양에 오신 것을 환영합니다</p>

                            </div>

                            {/* <div th:if="${error}"
                                className="mb-4 p-3.5 bg-red-50 border border-red-200 text-red-600 rounded-xl text-xs text-center font-medium">
                                <span th:text="${error}">오류 메시지</span>
                            </div>
                            <div th:if="${message}"
                                className="mb-4 p-3.5 bg-green-50 border border-green-200 text-green-600 rounded-xl text-xs text-center font-medium">
                                <span th:text="${message}">성공 메시지</span>
                            </div> */}

                            <form method="post" action="http://localhost:8088/api/auth/login">
                                <div className="relative mb-3 ">
                                    <div className="absolute inset-y-0 left-0 pl-3.5 flex items-center pointer-events-none">
                                        <svg className="h-5 w-5 text-gray-400" fill="none" viewBox="0 0 24 24"
                                            stroke="currentColor">
                                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5"
                                                d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z" />
                                        </svg>
                                    </div>
                                    <input type="email" name="email" placeholder="이메일을 입력하세요"
                                        className="w-full pl-11 pr-4 py-3.5 border border-gray-200 rounded-xl focus:outline-none focus:border-brand-green focus:ring-1 focus:ring-brand-green text-sm text-gray-700 placeholder-gray-400 bg-gray-50/50"
                                        required />
                                </div>

                                <div className="relative mb-5">
                                    <div className="absolute inset-y-0 left-0 pl-3.5 flex items-center pointer-events-none">
                                        <svg className="h-5 w-5 text-gray-400" fill="none" viewBox="0 0 24 24"
                                            stroke="currentColor">
                                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5"
                                                d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z" />
                                        </svg>
                                    </div>
                                    <input type="password" name="password" id="password" placeholder="비밀번호를 입력하세요"
                                        className="w-full pl-11 pr-11 py-3.5 border border-gray-200 rounded-xl focus:outline-none focus:border-brand-green focus:ring-1 focus:ring-brand-green text-sm text-gray-700 placeholder-gray-400 bg-gray-50/50"
                                        required />

                                        <button type="button"
                                            className="absolute inset-y-0 right-0 pr-3.5 flex items-center text-gray-400 hover:text-gray-600 focus:outline-none">
                                            <svg className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5"
                                                    d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5"
                                                    d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
                                            </svg>
                                        </button>
                                </div>

                                <div className="flex justify-end items-center gap-4">
                                    <button type="submit"
                                        className="w-full bg-brand-green hover:bg-brand-greenHover text-white py-3.5 rounded-xl transition">
                                        로그인
                                    </button>
                                </div>

                            </form>

                            <div className="mt-6 mb-6 flex items-center justify-center">
                                <div className="border-t border-gray-200 flex-grow"></div>
                                <span className="px-3 text-gray-400 text-xs">또는</span>
                                <div className="border-t border-gray-200 flex-grow"></div>
                            </div>

                            <a th:href="@{/oauth2/authorization/kakao}"
                                className="w-full bg-brand-kakao hover:bg-brand-kakaoHover text-[#3C1E1E] font-medium py-3.5 rounded-xl flex items-center justify-center gap-2 transition-colors duration-200 text-sm">
                                <svg className="w-4 h-4" viewBox="0 0 32 32" xmlns="http://www.w3.org/2000/svg">
                                    <path
                                        d="M16 4.64C9.231 4.64 3.743 8.761 3.743 13.845c0 3.284 2.302 6.166 5.811 7.822l-1.127 4.14c-.11.402.35.7.671.49l4.802-3.187c.683.094 1.385.143 2.1.143 6.769 0 12.257-4.122 12.257-9.205C28.257 8.76 22.769 4.64 16 4.64z"
                                        fill="#3C1E1E" />
                                    <text x="16" y="17" font-size="7" font-weight="bold" fill="#FEE500" text-anchor="middle"
                                        font-family="sans-serif">TALK
                                    </text>
                                </svg>
                                카카오톡으로 로그인
                            </a>

                            <div className="mt-7 text-center text-[13px]">
                                <span className="text-gray-500">아직 회원이 아니신가요?</span>
                                <a th:href="@{/signup}" className="text-[#4A8A54] font-medium hover:underline ml-1">회원가입</a>
                            </div>
                        </div>

                    </div>
                </div>
                <div th:replace="~{fragments/footer :: footer}"></div>

            </div>
        </>
    );
}

