import fs from 'node:fs';
import path from 'node:path';
import { fileURLToPath } from 'node:url';
import mysql from 'mysql2/promise';

const __dirname = path.dirname(fileURLToPath(import.meta.url));

if (!process.env.DATABASE_URL) {
  console.error('DATABASE_URL is not set. Check your .env file.');
  process.exit(1);
}

const connection = await mysql.createConnection({
  uri: process.env.DATABASE_URL,
  multipleStatements: true
});

try {
  const sqlPath = path.join(__dirname, 'db', '001_schema.sql');
  const sql = fs.readFileSync(sqlPath, 'utf8');
  process.stdout.write('Applying 001_schema.sql... ');
  await connection.query(sql);
  console.log('done\nMigration complete.');
} catch (err) {
  console.error('\nMigration failed:', err.message);
  process.exitCode = 1;
} finally {
  await connection.end();
}