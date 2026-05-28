import React from 'react';
import FacilityItem from "./item/FacilityItem.jsx";
import { useFavoriteContext } from "../../hooks/map/useFavoriteContext.js";

const FacilityFavoriteList = ({ selectedId, onSelectFacility }) => {
    const { favoriteList, loading } = useFavoriteContext();

    if (loading) {
        return <div className="text-center py-4 text-gray-400 animate-pulse text-sm">즐겨찾기 로딩 중...</div>;
    }

    if (favoriteList.length === 0) {
        return <div className="text-center py-8 text-gray-400 text-sm">등록된 즐겨찾기가 없습니다.</div>;
    }

    return (
        <>
            {favoriteList.map((item) => (
                <FacilityItem
                    key={item.id}
                    item={item.facility ? item.facility : item}
                    isSelected={selectedId === (item.facilityId || item.id)}
                    onClick={() => onSelectFacility(item.facility || item)}
                />
            ))}
        </>
    );
};

export default React.memo(FacilityFavoriteList);