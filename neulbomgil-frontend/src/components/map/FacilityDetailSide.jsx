import 'react';
import ParkItem from "./item/ParkItem.jsx";
import scoreTrue from '../../assets/score-true.png';
import { useFacilityFavorite } from '../../hooks/map/useFacilityFavorite.js';

const FacilityDetailSide = ({ facility, parks, onClose }) => {
    const IMAGE_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8088';

    const { isFavorite, toggleFavorite } = useFacilityFavorite(facility?.id);

    if (!facility) return null;

    return (
        <div
            id="detailSidebar"
            className="absolute left-[400px] w-[370px] h-full bg-white shadow-2xl z-20 border-l border-gray-100 flex flex-col"
        >
            {/* 닫기 버튼 */}
            <button
                id="closeSidebarBtn"
                onClick={onClose}
                className="absolute top-4 right-4 w-9 h-9 flex items-center justify-center bg-white shadow-md rounded-full hover:bg-gray-50 transition-all z-30 border border-gray-100 text-gray-500 text-lg"
            >
                ✕
            </button>

            {/* 메인 이미지 */}
            <div className="w-full h-60 bg-gray-100 shrink-0">
                <img
                    id="detailImage"
                    src={`${IMAGE_BASE_URL}${facility.facilityImage}`}
                    alt={facility.facilityName}
                    className="w-full h-full object-cover"
                />
            </div>

            {/* 콘텐츠 영역 */}
            <div className="flex-1 overflow-y-auto p-7">
                <div className="mb-6">
                    <span className="inline-block text-[11px] font-bold text-blue-600 bg-blue-50 px-2 py-0.5 rounded mb-2.5">
                        시설 상세정보
                    </span>
                    <div className="flex items-center justify-between gap-2">
                        <h2 id="detailName" className="text-2xl font-extrabold text-gray-900 leading-tight">
                            {facility.facilityName}
                        </h2>
                        {/* 즐겨찾기 버튼 */}
                        <button
                            id="favoriteBtn"
                            onClick={toggleFavorite}
                            className={`shrink-0 w-10 h-10 flex items-center justify-center rounded-full border transition-all ${
                                isFavorite
                                    ? "bg-red-50 border-red-100 text-red-500"
                                    : "bg-gray-50 border-gray-100 text-gray-400 hover:text-red-400"
                            }`}
                        >
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"
                                 fill={isFavorite ? "currentColor" : "none"} stroke="currentColor" strokeWidth="2"
                                 className="w-6 h-6">
                                <path strokeLinecap="round" strokeLinejoin="round"
                                      d="M21 8.25c0-2.485-2.099-4.5-4.688-4.5-1.935 0-3.597 1.126-4.312 2.733-.715-1.607-2.377-2.733-4.313-2.733C5.1 3.75 3 5.765 3 8.25c0 7.22 9 12 9 12s9-4.78 9-12z"/>
                            </svg>
                        </button>
                    </div>

                    {/* 별점 및 인원수 영역 */}
                    <div className="flex items-center mt-2.5 justify-between">
                        <div className="flex items-center space-x-1">
                            <div id="detailStars" className="flex items-center text-yellow-400"></div>
                            <span id="detailScore" className="text-sm font-bold text-gray-700 ml-1">
                                {Number(facility.facilityScore || 0).toFixed(1)}
                            </span>
                        </div>
                        <div className="flex items-center gap-2 text-[12px]">
                            <span className="flex items-center gap-1 font-bold text-gray-600 bg-gray-100 px-2 py-1 rounded-md">
                                정원 <span id="capacityCnt" className="text-blue-600">{facility.capacityCnt || 0}</span>
                            </span>
                            <span className="flex items-center gap-1 font-bold text-gray-600 bg-gray-100 px-2 py-1 rounded-md">
                                현원 <span id="currentCnt" className="text-green-600">{facility.currentCnt || 0}</span>
                            </span>
                        </div>
                    </div>
                </div>

                <div className="h-px bg-gray-100 w-full mb-6"></div>

                {/* 상세 정보 섹션 */}
                <div className="space-y-5 text-[15px]">
                    <div className="flex items-start">
                        <span className="mr-4 mt-0.5 w-6 text-center">📍</span>
                        <div className="flex-1">
                            <p id="detailNewAddress" className="font-medium text-gray-800 leading-snug">
                                {facility.newAddress}
                            </p>
                            <p id="detailLotAddress" className="text-xs text-gray-400 mt-1.5">
                                {facility.lotAddress || ""}
                            </p>
                        </div>
                    </div>

                    {facility.facilityTel && (
                        <div id="detailPhoneContainer" className="flex items-center">
                            <span className="mr-4 w-6 text-center">📞</span>
                            <p id="detailTel"
                               className="font-medium text-blue-600 hover:underline cursor-pointer tracking-wide">
                                {facility.facilityTel}
                            </p>
                        </div>
                    )}
                </div>

                <div className="h-px bg-gray-100 w-full my-8"></div>

                {/* 인근 공원 섹션 */}
                <div>
                    <h3 className="text-lg font-bold text-gray-900 mb-4 flex items-center gap-2">
                        <img src={scoreTrue} alt="공원"
                             className="w-7 h-7 object-contain border border-transparent rounded-full"/>
                        인근 공원 <span id="parkCount" className="text-green-600 text-sm font-medium">{parks?.length || 0}</span>
                    </h3>

                    <div id="nearbyParkList" className="flex flex-col gap-3">
                        {parks && parks.length > 0 ? (
                            parks.map((park) => (
                                <ParkItem key={park.id} park={park}/>
                            ))
                        ) : (
                            <div className="py-8 text-center bg-gray-50 rounded-xl border border-dashed border-gray-200">
                                <p className="text-sm text-gray-400 italic font-medium">주변 공원 정보가 없습니다.</p>
                            </div>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
};

export default FacilityDetailSide;