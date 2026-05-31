function ChatPopup({ chatOpen, selectedRoom, onCloseChatRoom }) {
    const chatSrc = selectedRoom
        ? `/chatrooms/${selectedRoom.roomId}/message?userId=${selectedRoom.userId}&partnerName=${encodeURIComponent(selectedRoom.name || '관리자')}`
        : 'about:blank';

    return (
        <section className={`chat-popup ${chatOpen ? 'active' : ''}`}>
            <div className="chat-header">
                <div className="chat-title">
                    {selectedRoom?.name || '관리자'}
                </div>

                <button
                    type="button"
                    className="chat-close-button"
                    onClick={onCloseChatRoom}
                >
                    ×
                </button>
            </div>

            <iframe
                className="chat-frame"
                src={chatSrc}
            />
        </section>
    );
}

export default ChatPopup;