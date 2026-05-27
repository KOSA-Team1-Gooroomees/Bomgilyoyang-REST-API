import FacilityItem from "./item/FacilityItem.jsx";
import {useFacilitySearch} from "../../hooks/map/useFacilitySearch.js";

const FacilitySearchList = ({coords, facilities, setFacilities, onSelectFacility, selectedId}) => {
    const {keyword, setKeyword, loading, lastElementRef} = useFacilitySearch(coords, setFacilities);
    return (
        <>
            <div className="p-4 bg-white border-b border-gray-100">
                <input
                    type="text" value={keyword}
                    onChange={(e) => setKeyword(e.target.value)}
                    placeholder="시설명을 검색하세요"
                    className="w-full px-4 py-3 rounded-xl bg-gray-50 border-none ring-1 ring-gray-200 focus:ring-2 focus:ring-blue-500 transition-all outline-none"
                />
            </div>
            <div className="flex-1 overflow-y-auto p-4 space-y-4">
                {facilities.map((item, index) => (
                    <FacilityItem
                        key={item.id}
                        item={item}
                        isSelected={selectedId === item.id}
                        onClick={() => onSelectFacility(item)}
                        ref={facilities.length === index + 1 ? lastElementRef : null}
                    />
                ))}
                {loading && <div className="text-center py-4 text-gray-400 animate-pulse text-sm">로딩 중...</div>}
            </div>
        </>
    );
};

export default FacilitySearchList;