-- Drop the old broken table first if it exists
DROP TABLE IF EXISTS SPRING_AI_CHAT_MEMORY;

CREATE TABLE SPRING_AI_CHAT_MEMORY (
    id UUID DEFAULT random_uuid() PRIMARY KEY,
    conversation_id VARCHAR(36) NOT NULL,
    content CLOB NOT NULL,
    metadata JSON,
    type VARCHAR(20) NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE INDEX idx_chat_memory_conv_id ON SPRING_AI_CHAT_MEMORY(conversation_id);

ALTER TABLE SPRING_AI_CHAT_MEMORY
ADD CONSTRAINT TYPE_CHECK CHECK (type IN ('USER', 'ASSISTANT', 'SYSTEM', 'TOOL'));