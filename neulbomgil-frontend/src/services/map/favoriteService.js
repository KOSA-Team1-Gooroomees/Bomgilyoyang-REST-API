import api from '../../api/axios.js';

/**
 * 즐겨찾기 추가
 * 이제 userId를 보내지 않고 facilityId만 보냅니다.
 * @param {string} facilityId
 */
export const addFavorite = (facilityId) => {
    return api.post('/api/favorites', {facilityId});
};

/**
 * 내 즐겨찾기 목록 조회
 * 더 이상 userId를 파라미터로 받지 않습니다.
 */
export const getFavorites = () => {
    return api.get('/api/favorites/me');
};

/**
 * 즐겨찾기 삭제
 * 경로에서 userId를 제거하고 고정된 /me 경로를 사용합니다.
 * @param {string} facilityId
 */
export const removeFavorite = (facilityId) => {
    return api.delete('/api/favorites/me', {
        data: {facilityId}
    });
};