import FacilitySearchList from '../components/map/FacilitySearchList.jsx';
import KakaoMap from '../components/map/KakaoMap.jsx';
import FacilityDetailSide from "../components/map/FacilityDetailSide.jsx";
import {useFacilityMap} from '../hooks/map/useFacilityMap.js';
import { FavoriteProvider } from '../context/favorite/FavoriteProvider.jsx';

const Map = () => {
    const {
        facilities, setFacilities,
        markers, parks, coords,
        selectedFacility, setSelectedFacility, isDetailOpen, setIsDetailOpen,
        handleBoundsChange, handleOpenDetail, handleToggleDetail
    } = useFacilityMap();

    return (
        <FavoriteProvider>
            <div className="flex flex-col md:flex-row w-screen h-screen overflow-hidden bg-white">
                <div className="w-full md:w-[400px] h-full flex flex-col border-r border-gray-200 z-10 shadow-lg">
                    <FacilitySearchList
                        coords={coords}
                        facilities={facilities}
                        setFacilities={setFacilities}
                        onSelectFacility={(item) => {
                            setSelectedFacility(item);
                            setIsDetailOpen(false);
                            handleOpenDetail(item.id);
                        }}
                        selectedId={selectedFacility?.id}
                    />
                </div>
                {isDetailOpen && (
                    <FacilityDetailSide
                        facility={selectedFacility}
                        parks={parks}
                        onClose={() => setIsDetailOpen(false)}
                    />
                )}

                <div className="flex-1 relative">
                    <KakaoMap
                        coords={coords}
                        markers={markers}
                        parks={parks}
                        onBoundsChange={handleBoundsChange}
                        selectedFacility={selectedFacility}
                        onSelectMarker={handleToggleDetail}
                    />
                </div>
            </div>
        </FavoriteProvider>
    );
};

export default Map;