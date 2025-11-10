-- Create CommentReport table for storing comment reports
-- This table stores reports from users about spam or inappropriate comments

IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[CommentReport]') AND type in (N'U'))
BEGIN
    CREATE TABLE [dbo].[CommentReport] (
        [reportID] UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
        [commentID] UNIQUEIDENTIFIER NOT NULL,
        [reporterID] UNIQUEIDENTIFIER NOT NULL,
        [reason] NVARCHAR(255) DEFAULT 'spam',
        [createAt] DATETIME DEFAULT GETDATE(),
        [status] NVARCHAR(50) DEFAULT 'pending', -- 'pending', 'resolved', 'dismissed'
        FOREIGN KEY ([commentID]) REFERENCES [dbo].[Comments]([CommentID]) ON DELETE CASCADE,
        FOREIGN KEY ([reporterID]) REFERENCES [dbo].[User]([userID]) ON DELETE CASCADE,
        CONSTRAINT CHK_CommentReport_Status CHECK ([status] IN ('pending', 'resolved', 'dismissed'))
    );
    
    -- Create index for faster queries
    CREATE INDEX IX_CommentReport_CommentID ON [dbo].[CommentReport]([commentID]);
    CREATE INDEX IX_CommentReport_Status ON [dbo].[CommentReport]([status]);
    CREATE INDEX IX_CommentReport_ReporterID ON [dbo].[CommentReport]([reporterID]);
    
    PRINT 'CommentReport table created successfully.';
END
ELSE
BEGIN
    PRINT 'CommentReport table already exists.';
END
GO

