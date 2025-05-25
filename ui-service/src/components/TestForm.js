import React, { useState } from 'react';
import {
    Box,
    Button,
    FormControl,
    InputLabel,
    MenuItem,
    Select,
    TextField,
    Typography
} from '@mui/material';

const TestForm = ({ onRunTest, isLoading }) => {
    const [requestCount, setRequestCount] = useState(100);
    const [testType, setTestType] = useState('quick');

    const handleSubmit = (e) => {
        e.preventDefault();
        onRunTest(requestCount, testType);
    };

    return (
        <Box component="form" onSubmit={handleSubmit} sx={{ mt: 3 }}>
            <Typography variant="h6" gutterBottom>
                Test Configuration
            </Typography>
            <Box sx={{ display: 'flex', gap: 2, alignItems: 'center' }}>
                <TextField
                    type="number"
                    label="Request Count"
                    value={requestCount}
                    onChange={(e) => setRequestCount(Number(e.target.value))}
                    InputProps={{ inputProps: { min: 1, max: 1000 } }}
                    sx={{ width: 150 }}
                />
                <FormControl sx={{ width: 150 }}>
                    <InputLabel>Test Type</InputLabel>
                    <Select
                        value={testType}
                        label="Test Type"
                        onChange={(e) => setTestType(e.target.value)}
                    >
                        <MenuItem value="quick">Quick</MenuItem>
                        <MenuItem value="delayed">Delayed</MenuItem>
                        <MenuItem value="large">Large Data</MenuItem>
                    </Select>
                </FormControl>
                <Button
                    type="submit"
                    variant="contained"
                    disabled={isLoading}
                    sx={{ height: 56 }}
                >
                    {isLoading ? 'Running...' : 'Run Test'}
                </Button>
            </Box>
        </Box>
    );
};

export default TestForm;