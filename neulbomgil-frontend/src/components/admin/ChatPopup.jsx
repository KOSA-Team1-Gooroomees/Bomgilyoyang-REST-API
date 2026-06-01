function ChatPopup({ chatOpen, selectedRoom, onCloseChatRoom }) {
  const chatSrc =
    chatOpen && selectedRoom?.roomId
      ? `/chatrooms/${selectedRoom.roomId}/message`
      : "about:blank";

  return (
    <section className={`chat-popup ${chatOpen ? "active" : ""}`}>
      <div className="chat-header">
        <div className="chat-title">
          {selectedRoom?.name || "관리자"}
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
        key={chatSrc}
        className="chat-frame"
        src={chatSrc}
        title="채팅방"
      />
    </section>
  );
}

export default ChatPopup;