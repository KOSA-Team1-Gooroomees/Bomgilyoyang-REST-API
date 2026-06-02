import { useState, useEffect } from "react";
import styles from "./Home.module.css";
import heroBg from "../assets/images/main/home-main.png";

const features = [
  {
    icon: "🌳",
    title: "나무로 보는 공원 환경 점수",
    desc: "시설 주변의 공원 접근성을 나무 아이콘으로 표시하여 산책하기 좋은 시설 선택을 도와드립니다.",
  },
  {
    icon: "📍",
    title: "지도 기반 시설 탐색",
    desc: "원하는 지역을 검색하면 지도에서 한눈에 비교하고 확인할 수 있습니다.",
  },
  {
    icon: "💬",
    title: "관리자 1:1 상담 채팅",
    desc: "문의사항에 대해 관리자와 실시간 1:1 채팅으로 빠르게 상담할 수 있습니다.",
  },
];

export default function Home() {
  const [visible, setVisible] = useState(false);

  useEffect(() => {
    setVisible(true);
  }, []);

  return (
    <div className={styles.wrap}>

      {/* ───── HERO ───── */}
      <section className={styles.hero}>
        {/* Background */}
        <div className={styles.heroBg}>
              <img 
              src={heroBg}
              alt="배경"
              style={{ width: "100%", height: "100%", objectFit: "cover" }}/>
        </div>

        {/* Overlay */}
        <div className={styles.heroOverlay} />

        {/* Hero Content */}
        <div className={`${styles.heroContent} ${visible ? styles.visible : ""}`}>
          <div className={styles.heroBadge}>🌿 봄날같은 요양 시설을 찾아드립니다</div>
          <h1 className={styles.heroTitle}>
            늘 봄날 같은 산책을 위한<br/>
            <span className={styles.heroTitleAccent}>복지시설 선택 지도 서비스</span>
          </h1>
          <p className={styles.heroSub}>
            원하는 지역을 검색하고<br/>
            주변 복지시설과 공원을 함께 확인해보세요
          </p>
          <a href="/map" className={styles.ctaBtn}>
            <span>시설 검색하러 가기</span>
            <span className={styles.arrow}>→</span>
          </a>
        </div>
      </section>

      {/* ───── INTRO ───── */}
      <section className={styles.intro}>
        <p className={styles.introText}>
          소중한 부모님의 노후를 위한 최선의 선택.<br/>
          <span className={styles.introTextDark}>
            봄길요양은 공원 접근성·시설 정보·실시간 상담까지<br/>
            필요한 모든 것을 한곳에 모았습니다.
          </span>
        </p>
      </section>

      {/* ───── FEATURES ───── */}
      <section className={styles.features}>
        <div className={styles.sectionInner}>
          <div className={styles.sectionHeader}>
            <span className={styles.badge}>서비스 소개</span>
            <h2 className={styles.sectionTitle}>봄길요양만의 특별한 서비스</h2>
          </div>
          <div className={styles.featuresGrid}>
            {features.map((f, i) => (
              <div key={i} className={styles.featureCard}>
                <div className={styles.iconWrap}>{f.icon}</div>
                <h3 className={styles.featureTitle}>{f.title}</h3>
                <p className={styles.featureDesc}>{f.desc}</p>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* ───── INFO ───── */}
      <section className={styles.info}>
        <div className={`${styles.sectionInner} ${styles.infoGrid}`}>
          {/* 일러스트 카드 */}
          <div className={styles.infoIllust}>
            <svg viewBox="0 0 560 400" xmlns="http://www.w3.org/2000/svg"
              style={{ width: "100%", height: "100%" }}>
              <defs>
                <linearGradient id="cardSky" x1="0" y1="0" x2="0" y2="1">
                  <stop offset="0%"   stopColor="#7ec8a0"/>
                  <stop offset="100%" stopColor="#a8ddb8"/>
                </linearGradient>
                <linearGradient id="cardGround" x1="0" y1="0" x2="0" y2="1">
                  <stop offset="0%"   stopColor="#3a7a50"/>
                  <stop offset="100%" stopColor="#2a5a3a"/>
                </linearGradient>
              </defs>
              <rect width="560" height="400" fill="url(#cardSky)" rx="28"/>
              <path d="M0,300 Q140,250 280,270 Q420,250 560,280 L560,400 L0,400 Z" fill="url(#cardGround)"/>
              <path d="M0,340 Q140,310 280,325 Q420,310 560,330 L560,400 L0,400 Z" fill="#224838"/>
              <rect x="220" y="290" width="120" height="8" rx="4" fill="#8b6340"/>
              <rect x="228" y="298" width="8"   height="20" rx="3" fill="#7a5530"/>
              <rect x="324" y="298" width="8"   height="20" rx="3" fill="#7a5530"/>
              <rect x="216" y="278" width="128" height="7"  rx="3" fill="#a07848"/>
              {[[80,240],[130,220],[460,235],[510,215]].map(([x,y],i) => (
                <g key={i}>
                  <rect x={x-5} y={y+36} width={10} height={36} rx={4} fill="#6b4226"/>
                  <ellipse cx={x} cy={y}    rx={34} ry={44} fill="#2d7045" opacity="0.9"/>
                  <ellipse cx={x} cy={y-14} rx={24} ry={32} fill="#3d8a55" opacity="0.85"/>
                  <ellipse cx={x} cy={y-26} rx={16} ry={22} fill="#4da065" opacity="0.8"/>
                </g>
              ))}
              {[[180,308],[200,312],[350,306],[370,310],[390,308]].map(([x,y],i) => (
                <g key={i}>
                  <circle cx={x} cy={y} r={5}   fill="#f9c0d0" opacity="0.9"/>
                  <circle cx={x} cy={y} r={2.5} fill="#f4a0b0"/>
                </g>
              ))}
              <ellipse cx="280" cy="370" rx="60" ry="20" fill="rgba(180,150,100,0.5)"/>
              <circle cx="440" cy="70" r="38" fill="rgba(255,230,120,0.5)"/>
              <circle cx="440" cy="70" r="26" fill="rgba(255,225,100,0.65)"/>
              <circle cx="440" cy="70" r="16" fill="rgba(255,220,80,0.8)"/>
              {[[150,70],[165,60],[178,72]].map(([x,y],i) => (
                <path key={i} d={`M${x},${y} Q${x+6},${y-6} ${x+12},${y}`}
                  stroke="rgba(255,255,255,0.7)" strokeWidth="1.5" fill="none" strokeLinecap="round"/>
              ))}
              <text x="50%" y="92%" textAnchor="middle"
                style={{ font: "bold 15px 'Noto Sans KR', sans-serif" }}
                fill="rgba(255,255,255,0.85)" letterSpacing="2">
                봄날 같은 산책로를 함께
              </text>
            </svg>
          </div>

          {/* 텍스트 */}
          <div className={styles.infoText}>
            <span className={styles.badge}>왜 봄길요양인가요?</span>
            <h2 className={styles.infoTitle}>
              부모님의 산책이<br/>
              <span className={styles.infoTitleAccent}>더 행복해지는 선택</span>
            </h2>
            <p className={styles.infoDesc}>
              요양시설을 선택할 때 가장 중요한 것 중 하나는 주변 환경입니다.
              봄길요양은 각 시설의 공원 접근성을 직관적인 나무 점수로 안내하여,
              어르신이 자연 속에서 산책하고 휴식할 수 있는 최적의 시설을
              쉽게 찾을 수 있도록 도와드립니다.
            </p>
            {[
              "📍 지역별 시설 지도 탐색",
              "🌳 공원 접근성 점수 제공",
              "💬 전문 상담사 1:1 채팅",
            ].map((item, i, arr) => (
              <div key={i} className={styles.infoItem}
                style={{ borderBottom: i < arr.length - 1 ? "1px solid #e8f4ee" : "none" }}>
                {item}
              </div>
            ))}
          </div>
        </div>
      </section>
    </div>
  );
}