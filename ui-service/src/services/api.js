import axios from 'axios';

const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080';

export const performanceApi = {
    runTest: async (requestCount, testType) => {
        try {
            const response = await axios.get(`${API_BASE_URL}/api/performance/test`, {
                params: {
                    requestCount,
                    testType
                }
            });
            return response.data;
        } catch (error) {
            console.error('Error running performance test:', error);
            throw error;
        }
    }
};