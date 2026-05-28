export default {
  // 로그인 관련 컴포넌트 폴더 지정
  content: ["./src/pages/Register.{js,jsx,ts,tsx}"], 
  theme: {
    extend: {
      colors: {
        brand: {
          green: '#5CA668',
          greenHover: '#4A8A54',
          kakao: '#FEE500',
          kakaoHover: '#E5CE00'
        }
      },
      keyframes: {
        fadeIn: { '0%': { opacity: '0' }, '100%': { opacity: '1' } },
        scaleUp: { '0%': { transform: 'scale(0.95)', opacity: '0' }, '100%': { transform: 'scale(1)', opacity: '1' } }
      },
      animation: {
        'fade-in': 'fadeIn 0.2s ease-out forwards',
        'scale-up': 'scaleUp 0.3s cubic-bezier(0.34, 1.56, 0.64, 1) forwards'
      }
    },
  },
  plugins: [],
}