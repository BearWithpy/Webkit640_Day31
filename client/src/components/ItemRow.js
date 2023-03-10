import { useState } from "react"

function ItemRow({ item, removeItem, updateItem }) {
    const [mode, setMode] = useState(false)
    const [title, setTitle] = useState(item.title)
    return (
        <li>
            <p>
                <input
                    checked={item.done ? "checked" : ""}
                    type="checkbox"
                    onChange={(e) => {
                        item.done = e.target.checked
                        updateItem(item)
                    }}
                />
                <input
                    value={title}
                    onChange={(e) => {
                        setTitle(e.target.value)
                    }}
                    className={item.done ? "done" : "not-done"}
                    type="text"
                    readOnly={mode ? "" : "readonly"}
                    onClick={(e) => {
                        setMode(true)
                    }}
                    onBlur={(e) => {
                        item.title = title
                        updateItem(item)
                        setMode(false)
                    }}
                />
                <button
                    onClick={(e) => {
                        removeItem(item)
                    }}
                >
                    삭제
                </button>
                <button
                    onClick={(e) => {
                        setMode(!mode)
                        if (mode) {
                            item.title = title
                            updateItem(item)
                        } else {
                        }
                    }}
                >
                    {mode ? "수정완료" : "수정"}
                </button>
            </p>
        </li>
    )
}

export default ItemRow
