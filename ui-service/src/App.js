import React, { useState } from 'react';
import { Container, Box, Typography, Alert } from '@mui/material';
import TestForm from './components/TestForm';
import PerformanceChart from './components/PerformanceChart';
import ResultsTable from './components/ResultsTable';
import { performanceApi } from './services/api';
import './App.css';

function App() {
    const [results, setResults] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const handleRunTest = async (requestCount, testType) => {
        setLoading(true);
        setError(null);
        try {
            const data = await performanceApi.runTest(requestCount, testType);
            setResults(data);
        } catch (err) {
            setError('Failed to run performance test. Please try again.');
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    return (
        <Container maxWidth="lg">
            <Box sx={{ my: 4 }}>
                <Typography variant="h4" component="h1" gutterBottom>
                    OpenFeign vs WebClient Performance Comparison
                </Typography>

                {error && (
                    <Alert severity="error" sx={{ mb: 2 }}>
                        {error}
                    </Alert>
                )}

                <TestForm onRunTest={handleRunTest} isLoading={loading} />

                {results && (
                    <>
                        <PerformanceChart data={results} />
                        <ResultsTable data={results} />
                    </>
                )}
            </Box>
        </Container>
    );
}

export default App;