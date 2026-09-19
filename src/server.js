import express from 'express';
import mysql from 'mysql2/promise';
import path from 'node:path';
import { fileURLToPath } from 'node:url';

const __dirname = path.dirname(fileURLToPath(import.meta.url));
const app = express();
app.use(express.json());

// Serve static frontend from public folder
app.use(express.static(path.join(__dirname, '..', 'public')));

const pool = mysql.createPool(process.env.DATABASE_URL);

// API: Get all orders
app.get('/api/orders', async (req, res) => {
    try {
        const [rows] = await pool.query('SELECT * FROM orders ORDER BY created_at DESC');
        res.json(rows);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

// API: Mutating action with audit log
app.post('/api/orders/:id/actions', async (req, res) => {
    const { id } = req.params;
    const { action_type, reason, admin_id } = req.body;
    try {
        await pool.query(
            'INSERT INTO admin_actions (admin_id, action_type, entity_type, entity_id, reason) VALUES (?, ?, ?, ?, ?)',
            [admin_id || 'akhil_admin', action_type, 'order', id, reason || 'No reason provided']
        );
        res.json({ success: true, message: `Action ${action_type} recorded and audited successfully.` });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

const PORT = process.env.PORT || 4000;
app.listen(PORT, () => {
    console.log(`Qikro Ops Server running live at http://localhost:${PORT}`);
});