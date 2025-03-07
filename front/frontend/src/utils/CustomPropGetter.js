
export const CustomPropGetter = (event) => {

    const eventColors = {
      High: "#FFA7A7",  
      Middle: "#FFE08C", 
      Low: "#bdd299", 
      Default: "#C7D3ED",
    };
  
    const backgroundColor = eventColors[event.priorityName] || eventColors.Default;
    
    return {
      style: {
        background: backgroundColor, 
        color: "white", 
        padding: "5px",
        borderRadius: "4px",
        border: `1px solid ${backgroundColor}`, // ✅ 각 이벤트 색상과 동일한 테두리 적용
      },
    };
  };