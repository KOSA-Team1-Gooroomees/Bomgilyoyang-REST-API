import 'react';
import { useFavorites } from '../hooks/map/useFavorites';

function FavoriteTest() {
    const TEST_USER_ID = 1;

    const { list, loading, error } = useFavorites(TEST_USER_ID);

    if (loading) return <div>로딩 중...</div>;
    if (error) return <div>데이터를 불러오는 중 오류가 발생했습니다.</div>;

    return (
        <pre style={{
            background: '#eee',
            padding: '15px',
            borderRadius: '5px',
            fontSize: '12px',
            overflowX: 'auto'
        }}>
            {JSON.stringify(list, null, 2)}
        </pre>
    );
}

export default FavoriteTest;