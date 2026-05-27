import { useState, useEffect, useCallback } from 'react';
import { getFavorites } from '../../services/map/favoriteService.js'

export function useFavorites(userId) {
    const [list, setList] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const fetchList = useCallback(async () => {
        if (!userId) return;

        setLoading(true);
        setError(null);
        try {
            const res = await getFavorites(userId);
            setList(res.data || []);
        } catch (err) {
            console.error("데이터 로드 실패:", err);
            setError(err);
        } finally {
            setLoading(false);
        }
    }, [userId]);

    useEffect(() => {
        fetchList();
    }, [fetchList]);

    return { list, loading, error, refetch: fetchList };
}